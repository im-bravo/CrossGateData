package cg.data.resource.inputStream;

import static cg.base.util.IOUtils.PATH_SPLIT;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cg.base.log.Log;
import cg.base.util.IOUtils;
import cg.base.util.URLHandler;
import cg.data.resource.AWSS3;

public abstract class TypeInputStreamHandler<T> implements InputStreamHandler<T> {
	
	protected List<URI> uris = new LinkedList<URI>();
	
	protected Map<String, T> resources = new HashMap<String, T>();
	
	protected final String type;
	
	protected final Log log;
	
	public TypeInputStreamHandler(String type, Log log) {
		this.type = type;
		this.log = log;
	}

	@Override
	public void reload() throws Exception {
		for (URI uri : uris) {
			String name = IOUtils.decode(uri.toString());
			int lastDotIndex = name.lastIndexOf(".");
			name = name.substring(0, lastDotIndex);
			int index;
			if ((index = name.lastIndexOf(PATH_SPLIT)) > -1) {
				name = name.substring(index + 1);
			}
			add(name, get(uri));
		}
	}
	
	protected abstract T get(URI uri) throws Exception;
	
	protected void add(String name, T value) {
		resources.put(name, value);
	}

	@Override
	public void addURI(URI uri) {
		uris.add(uri);
	}

	@Override
	public T get(String name) {
		return resources.get(name.replace(".txt", ""));
	}

	@Override
	public void clear() {
		resources.clear();
	}

	@Override
	public Collection<DataInfo> getDataInfos() {
		Collection<DataInfo> collection = new ArrayList<DataInfo>(uris.size());
		for (URI uri : uris) {
			collection.add(new DataInfo(uri.toString(), type));
		}
		return collection;
	}
	
	protected static void getStream(URI uri, URLHandler handler) throws Exception {
		if (AWSS3.isAWS_S3(uri)) {
			AWSS3.requestS3ToStream(uri, handler);
		} else {
			IOUtils.getStream(uri, handler);
		}
	}

}