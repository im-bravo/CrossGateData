package cg.data.resource.loader;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

import cg.data.resource.AWSS3;

import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AWSS3ResourceLoader implements ServerResourceLoader {

	@Override
	public void load(String serverPath, Function<URI, Void> singleResourceLoader) throws Exception {
		String[] infos = serverPath.split("\\/");
		List<S3ObjectSummary> objectListing = AWSS3.listObjects(infos[0].split("\\.")[0], infos[1] + "/");
		for (S3ObjectSummary objectSummary : objectListing) {
		    if (objectSummary.getSize() > 0) {
		    	URI uri = new URI(AWSS3.SCHEME_S3 + "://" + infos[0] + "/" + objectSummary.getKey());
		    	singleResourceLoader.apply(uri);
		    }
		}
	}

}
