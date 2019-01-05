package com.acloudysky.auth;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;


/**
 * Creates an authenticated client which is allowed to use the selected service REST API. 
 * <p>
 * <b>Note</b>. You need to set up your AWS security credentials to be able to use the API. 
 * You do this by creating a file named "credentials" at ~/.aws/ (C:\Users\USER_NAME\.aws\ for Windows users) 
 * and saving the following lines in the file:
 * </p>
 *<pre>
 * [default]
 * aws_access_key_id = your access key
 * aws_secret_access_key = your secret key
 * </pre>
 * 
 * @author Michael Miele
 *
 */
public class AuthenticateAwsServiceClient extends AwsServiceClientAuthentication
		implements IAwsServiceClientAuthentication {

	
	/**
	 * Instantiates the AuthenticateAwsServiceClient class. It also initializes the
	 * AwsServiceClientAuthentication parent class.  
	 */
	public AuthenticateAwsServiceClient() {
		super();
	}
	
	/**
	 * Creates an authenticated client to access the S3 service through its REST API. 
	 * @return Authenticated client.
	 * @throws AmazonClientException Base exception class for any errors that occur while attempting to use an AWS client 
	 * to make service calls to Amazon Web Services
	 * @see com.amazonaws.services.ec2.AmazonEC2 
	 */
	public AmazonS3 getAuthenticatedS3Client() throws AmazonClientException {
		
		// Get the user's credentials so the application can access the service REST API.
		AWSCredentials credentials = getAuthenticationCredentials();
		
		// Get the authenticated client.
		AmazonS3Client s3Client = new AmazonS3Client(credentials);
		
	    // Return the authenticated client. 
		return s3Client;
	}

	/**
	 * Creates an authenticated client to access the EC2 service through its REST API. 
	 * @param selectedRegion The client selected region.
	 * @return Authenticated client.
	 * @throws AmazonClientException Base exception class for any errors that occur while attempting to use an AWS client 
	 * to make service calls to Amazon Web Services
	 * @see com.amazonaws.services.ec2.AmazonEC2 
	 */
	public AmazonEC2 getAuthenticatedEC2Client(Regions selectedRegion) throws AmazonClientException {
		// Get the user's credentials so the application can access the service REST API.
		AWSCredentials credentials = getAuthenticationCredentials();
		// Get the authenticated client.
		AmazonEC2Client ec2Client = new AmazonEC2Client(credentials);
		// Set the client regiopn.
		Region currentRegion = Region.getRegion(selectedRegion);
		if (DEBUG)
			System.out.println(String.format("%s", currentRegion.toString()));
        ec2Client.setRegion(currentRegion);

        // Return the authenticated client. 
		return ec2Client;
	}
	
	

}
