package com.liferay.apio.groundnut;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class BackendEntryPoint {

	private static BackendEntryPoint INSTANCE = new BackendEntryPoint();

	private BackendEntryPoint () {
	}

	public static BackendEntryPoint instance() {
		return INSTANCE;
	}

	public String get(String address, String username, String password) {
		return get(URI.create(address), username, password, "application/json");
	}

	public String get(URI address, String username, String password) {
		return get(address, username, password, "application/json");
	}
	
	public String get(String address, String username, String password, String accept) {
		return get(URI.create(address), username, password, accept);
	}

	public String get (URI address, String username, String password, String accept) {

		HttpHost targetHost = new HttpHost(address.getHost(), address.getPort(), address.getScheme());
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				new AuthScope (targetHost.getHostName(), targetHost.getPort()), 
				new UsernamePasswordCredentials(username, password));

		CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
		
		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());

		final HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		
		HttpUriRequest req = new HttpGet(address);
		req.setHeader("Accept", accept);

		try {
			HttpResponse resp = httpClient.execute(targetHost, req, context);
			if (resp.getStatusLine().getStatusCode() != 200) {
				System.out.println(resp);
				return null;
			}
			return EntityUtils.toString(resp.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;

	}
	
}
