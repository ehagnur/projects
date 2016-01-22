import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.arjun.mapreduce.patterns.mapreducepatterns.MRDPUtils;

public class MinMax {

    public static class MinMaxCountMapper extends 
            Mapper<Object, Text, Text, MinMaxCountTuple> {

        private Text outuserId = new Text();
        private MinMaxCountTuple outTuple = new MinMaxCountTuple();

        private final static SimpleDateFormat sdf = 
                     new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");

        @Override
        protected void map(Object key, Text value,
                Context context)
                throws IOException, InterruptedException {

            Map<String, String> parsed = MRDPUtils.transformXMLtoMap(value.toString());

            String date = parsed.get("CreationDate");
            String userId = parsed.get("UserId");

            try {
                Date creationDate = sdf.parse(date);
                outTuple.setMin(creationDate);
                outTuple.setMax(creationDate);
            } catch (java.text.ParseException e) {
                System.err.println("Unable to parse Date in XML");
                System.exit(3);
            }

            outTuple.setCount(1);
            outuserId.set(userId);

            context.write(outuserId, outTuple);

        }

    }

    public static class MinMaxCountReducer extends 
            Reducer<Text, MinMaxCountTuple, Text, MinMaxCountTuple> {

        private MinMaxCountTuple result = new MinMaxCountTuple();


        protected void reduce(Text userId, Iterable<MinMaxCountTuple> values,
                Context context)
                throws IOException, InterruptedException {

            result.setMin(null);
            result.setMax(null);
            result.setCount(0);
            int sum = 0;
            int count = 0;
            for(MinMaxCountTuple tuple: values )
            {
                if(result.getMin() == null || 
                        tuple.getMin().compareTo(result.getMin()) < 0) 
                {
                    result.setMin(tuple.getMin());
                }

                if(result.getMax() == null ||
                        tuple.getMax().compareTo(result.getMax()) > 0)  {
                    result.setMax(tuple.getMax());
                }

                System.err.println(count++);

                sum += tuple.getCount();
            }

            result.setCount(sum);
            context.write(userId, result);
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String [] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if(otherArgs.length < 2 )
        {
            System.err.println("Usage MinMaxCout input output");
            System.exit(2);
        }


        Job job = new Job(conf, "Summarization min max count");
        job.setJarByClass(MinMax.class);
        job.setMapperClass(MinMaxCountMapper.class);
        job.setCombinerClass(MinMaxCountReducer.class);
        job.setReducerClass(MinMaxCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(MinMaxCountTuple.class);

        FileInputFormat.setInputPaths(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        boolean result = job.waitForCompletion(true);
        if(result)
        {
            System.exit(0);
        }else {
            System.exit(1);
        }

    }

}
