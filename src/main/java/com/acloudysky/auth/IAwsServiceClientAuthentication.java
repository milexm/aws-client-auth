package com.acloudysky.auth;

import java.util.HashMap;

import com.amazonaws.AmazonClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.s3.AmazonS3;

/**
 * Defines variables and methods to authenticate clients so they can use the selected AWS service REST APIs. 
 * @author Michael
 *
 */
public interface IAwsServiceClientAuthentication {
	
	/**
	 * AWS EC2 US regions  
	 * <p>
	 * <b>Note</b>: Only US regions are used, for simplicity.
	 * </p>
	 */
	HashMap<String, Enum<Regions>> ec2Regions = new HashMap<String, Enum<Regions>>()
	{ 
		// Avoid compiler warning. 
		private static final long serialVersionUID = 1L;
		// Initialize the ec2Regions. 
		{
			put("us-east-1", Regions.US_EAST_1); // US East (N. Virginia) 
			put("us-west-1", Regions.US_WEST_1); // US West (N. California)
			put("us-west-2", Regions.US_WEST_2); // US West (Oregon)	
		}
	};
	
	
	
	/**
	 * Creates an authenticated client to access the S3 service through its REST API. 
	 * @return Authenticated S3 client.
	 * @throws AmazonClientException Base exception class for any errors that occur while attempting to use an AWS client 
	 * to make service calls to Amazon Web Services
	 */
	AmazonS3 getAuthenticatedS3Client() throws AmazonClientException;
	

	/**
	 * Creates an authenticated client to access the EC2 service through its REST API. 
	 * @param regionValue The service region to use. 
	 * @return Authenticated EC2 client.
	 * @throws AmazonClientException Base exception class for any errors that occur while attempting to use an AWS client 
	 * to make service calls to Amazon Web Services
	 */
	AmazonEC2 getAuthenticatedEC2Client(Regions regionValue) throws AmazonClientException;
	
	
	
	
}
