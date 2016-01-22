import java.io.IOException;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
        
public class SplitFile {                      
 public static void main(String[] args) throws Exception {
	 Configuration conf = new Configuration();
	 FileSystem hdfs = FileSystem.get(conf);
	 Path inFile = new Path(args[0]);
	 int nOfFiles = Integer.parseInt(args[1]);
	 	 
	 //FileStatus[] inputFile = local.listStatus(inFile);
	 FSDataInputStream in = hdfs.open(inFile);
	 @SuppressWarnings("deprecation")
	 int HDPFileSize=(int) hdfs.getLength(inFile);
	 int localFileSize=HDPFileSize/nOfFiles;
	 try{
		 int i = 0;
		 while(i<nOfFiles){
			 Path path = new Path("File"+i+".txt");
			 FSDataOutputStream out = hdfs.create(path);
			 byte buffer[]=new byte[localFileSize];
			 int bytesRead=0;	
             
	            if ( (bytesRead=in.read(buffer))>0) {             	
	                out.write(buffer,0,bytesRead);
	                out.close();
		 }
	            i++;
	  }
	 }catch (IOException e){
		 e.printStackTrace();
	 }
   }       
}
