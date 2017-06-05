package master_spring_mvc.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import master_spring_mvc.search.cache.SearchCache;

@Service
@Profile("async")
public class ParallelSearchService implements TwitterSearch {
	private final AsyncSearch asyncSearch;
	
	@Autowired
	public ParallelSearchService(AsyncSearch asyncSearch) {
		this.asyncSearch = asyncSearch;
	}
	
	@Override
	public List<LightTweet> search(String searchType, List<String> keywords) {
		CountDownLatch latch = new CountDownLatch(keywords.size());
		List<LightTweet> allTweets = Collections.synchronizedList(new ArrayList<>());
		keywords
			.stream()
			.forEach(keyword -> asyncFetch(latch, allTweets, searchType, keyword));
		await(latch);
		return allTweets;
	}	

	private void asyncFetch(CountDownLatch latch, List<LightTweet> allTweets, String searchType, String keyword) {
		asyncSearch.asyncFetch(searchType, keyword)
			.addCallback(
					tweets -> onSucces(allTweets, latch, tweets),
					ex -> onError(latch, ex));		
	}

	private void await(CountDownLatch latch) {
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
		
	}
	
	private static void onSucces(List<LightTweet> results, CountDownLatch latch, List<LightTweet> tweets) {
		results.addAll(tweets);		
		latch.countDown();
	}
	
	private static void onError(CountDownLatch latch, Throwable ex) {
		ex.printStackTrace();
		latch.countDown();
	}

	@Component
	private static class AsyncSearch {
		private final Log logger = LogFactory.getLog(getClass());
		private SearchCache searchCache;
		
		@Autowired
		public AsyncSearch(SearchCache searchCache) {
			this.searchCache = searchCache;
		}
		
		@Async
		public ListenableFuture<List<LightTweet>> asyncFetch(String searchType, String keyword) {
			logger.info(Thread.currentThread().getName() + " - Wyszukiwane słowa " + keyword);
			return new AsyncResult<>(searchCache.fetch(searchType, keyword));
		}
	}

}
