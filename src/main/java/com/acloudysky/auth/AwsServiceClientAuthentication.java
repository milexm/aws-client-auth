package com.acloudysky.auth;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/***
 * Obtains the credentials for the client application to allow the use of the requested AWS service REST API.
 * <p><b>Note</b> To connect to any of the supported services with the AWS SDK for Java, you must provide AWS credentials. 
 * The AWS SDKs and CLIs use provider chains to look for AWS credentials in a number of different places, including system/user 
 * environment variables and local AWS configuration files.
 * This application assumes that the credentials are stored in the local file named <b>credentials</b> 
 * at ~/.aws/ (*C:\Users\USER_NAME.aws* for Windows users) and saving the following lines in the file:
 * <pre>
 * [default]
	aws_access_key_id = your access key
	aws_secret_access_key = your secret key
 * </pre>
 * For more information, see <a href="http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html" 
 * target="_blank">Set up AWS Credentials and Region for Development</a>
 */
public abstract class AwsServiceClientAuthentication {
	
	// Debug flag to use for testing purposes.
	public static boolean DEBUG = false;
	
	// Local class variables. 
	private String OS, userHome;
	private static String credentialsFilePath;
	
	/**
	 * Environment parameters such as OS name, user's home directory. 
	 */
	ArrayList<String> environment = new  ArrayList<String>(
			Arrays.asList(
					System.getProperty("os.name"), 		// OS name.
					System.getProperty("user.home")		// User home directory.
			)
	);
	
	/**
	 * Initializes the AwsClientAuthentication class. 
	 */
	public AwsServiceClientAuthentication() {
		ArrayList<String> env = environment;
		Iterator<String> i = env.iterator();
		OS = i.next();
		userHome = i.next();
	
		// Determine credential directory file path.
    	if (OS.startsWith("Windows"))
		  credentialsFilePath = userHome.concat("\\" + ".aws\\credentials");
        else 
        	if (OS.startsWith("Mac")) {
        		credentialsFilePath = "~/.aws/credentials";
    			// credentialsFilePath = userHome.concat("/.aws/credentials");
    			System.out.println(OS.toString()); }
    	 if (DEBUG) 
    		 // System.out.println(userHome.concat("/.aws/credentials"));
    		 System.out.println(credentialsFilePath);
    	 	
	}
	
	
	/**
	 * Gets user's credentials. 
	 * They will be used by the application to access the service REST API.
	 * @return credentials From the user's credentials.
	 */
	protected AWSCredentials getAuthenticationCredentials() {
		AWSCredentialsProvider credentialsProvider;
    	AWSCredentials credentials;
    	 
    	File credentialsFile = null;
    	
    	try 
		{
		  credentialsProvider = new ProfileCredentialsProvider();
		  credentials = credentialsProvider.getCredentials();
		  
      	} 
		catch (Exception e) {
			credentialsFile = new File(System.getProperty(credentialsFilePath));
      		throw new AmazonClientException(
                  "Cannot load the credentials from the credential profiles file. " +
                  "Please make sure that your credentials file is at the correct " +
                  "location " + credentialsFile.getAbsolutePath() + " and is in valid format.", e);
      	}
    	
    	return credentials;
		
	}

}
