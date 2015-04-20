import java.io.IOException;
import java.util.*;
        
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
        
public class CommonFriends {
        
 public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private final static IntWritable zero = new IntWritable(0);
    private Text friendPair = new Text();
        
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line, " \t,");
        String user = null;
        if (tokenizer.hasMoreTokens()) {
            user = tokenizer.nextToken();
            Vector<String> friends = new Vector<String>();
            while(tokenizer.hasMoreTokens()) {
            	String f = tokenizer.nextToken();
            	friends.add(f);
            	friendPair.set(user + "," + f);
            	context.write(friendPair, zero);
            }
            for (int i = 0; i < friends.size(); i++) {
            	for (int j = 0; j < friends.size(); j++) {
            		if (i == j) continue;
            		friendPair.set(friends.get(i) + "," + friends.get(j));
            		context.write(friendPair, one);
            	}
            }
            
        }
    }
 } 
        
 public static class Reduce extends Reducer<Text, IntWritable, Text, Text> {

    public void reduce(Text key, Iterable<IntWritable> values, Context context) 
      throws IOException, InterruptedException {
        int sum = 0;
        int prod = 1;
        Text first = new Text();
        Text second = new Text();
        for (IntWritable val : values) {
            sum += val.get();
            prod = prod * val.get();
        }
        if (prod > 0) {
        	String[] users = key.toString().split(",");
        	first.set(users[0]);
        	second.set(users[1] + " " + sum);
        	context.write(first, second);
        	
        }
    }
 }
    
 
 public static class Map2 extends Mapper<LongWritable, Text, Text, Text> {

	        
	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	        String line = value.toString();
	        String[] friends = line.split("[ \t]");
	        if (friends.length >= 3) {
	        	Text user = new Text();
	        	user.set(friends[0]);
	        	Text fCount = new Text();
	        	fCount.set(friends[1] + "," + friends[2]);
	        	context.write(user, fCount);
	        }
	       
	    }
	 } 
	        
	 public static class Reduce2 extends Reducer<Text, Text, Text, Text> {

	    public void reduce(Text key, Iterable<Text> values, Context context) 
	      throws IOException, InterruptedException {
	       
	        List<String> fCounts = new LinkedList<String>();
	        Text refriends = new Text();
	        StringBuffer recommendFriends = new StringBuffer();
	        for (Text val : values) {
	        	fCounts.add(val.toString());
	        }
	        int total =0;
			while (fCounts.size() > 0 && total < 10) {
				int largest = 0;
				int index = 0;
		        String rcf = null;
				for (int i = 0; i < fCounts.size(); i++) {
					String[] fc = fCounts.get(i).split(",");
					String f = fc[0];
					int c = Integer.parseInt(fc[1]);
					if (c > largest) {
						largest = c;
						index = i;
						rcf = f;

					}
				}
				total++;
				recommendFriends.append(" " + rcf);
				fCounts.remove(index);
			}
			refriends.set(recommendFriends.toString());
			context.write(key, refriends);

	    }
	 }
	         
 public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
        
    Job job = new Job(conf, "CommonFriends");
    job.setJarByClass(CommonFriends.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
        
    job.setMapperClass(Map.class);
    job.setReducerClass(Reduce.class);
        
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
        
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
    int code = job.waitForCompletion(true)?0:1;
    
    if(code == 0){
    Configuration conf2 = new Configuration();
    
    Job job2 = new Job(conf2, "CommonFriends");
    job2.setJarByClass(CommonFriends.class);
    job2.setOutputKeyClass(Text.class);
    job2.setOutputValueClass(Text.class);
        
    job2.setMapperClass(Map2.class);
    job2.setReducerClass(Reduce2.class);
        
    job2.setInputFormatClass(TextInputFormat.class);
    job2.setOutputFormatClass(TextOutputFormat.class);
        
    FileInputFormat.addInputPath(job2, new Path(args[1]));
    FileOutputFormat.setOutputPath(job2, new Path(args[2]));
        
    code = job2.waitForCompletion(true)?0:1;
    }
    System.exit(code);
 } 
}
