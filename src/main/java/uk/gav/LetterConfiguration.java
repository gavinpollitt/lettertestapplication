package uk.gav;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import uk.gav.batch.LineListener;
import uk.gav.date.DateProvider;
import uk.gav.letter.LetterSource;
import uk.gav.records.Record;

@Configuration
@ConfigurationProperties("batch")
@EnableAsync
public class LetterConfiguration {
	@Autowired
	private ApplicationContext context;

	private List<String> lineListeners;
	private List<String> letterSources;
	private String		 dateProvider;

	@Bean
	public TaskExecutor getTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(1);
		threadPoolTaskExecutor.setMaxPoolSize(1);
		return threadPoolTaskExecutor;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public DateProvider getDateProvider() {
		DateProvider clazz = null;
		try {
			clazz = (DateProvider) context.getAutowireCapableBeanFactory().createBean(Class.forName(this.dateProvider));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load service class: " + this.dateProvider + "::" + e);
		}
		return clazz;		
	}
	
	@Bean(name = "lineListeners")
	public List<LineListener> getListeners() {
		return this.lineListeners.stream().map(this::getListener).collect(Collectors.toList());
	}

	private LineListener getListener(final String className) {
		LineListener clazz = null;
		try {
			clazz = (LineListener) context.getAutowireCapableBeanFactory().createBean(Class.forName(className));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load service class: " + className + "::" + e);
		}
		return clazz;
	}

	@Bean(name = "letterSources")
	public List<LetterSource<? extends Record>> getSources() {
		return this.letterSources.stream().map(this::getSource).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private LetterSource<? extends Record> getSource(final String className) {
		LetterSource<? extends Record> clazz = null;
		try {
			clazz = (LetterSource<? extends Record>) context.getAutowireCapableBeanFactory().createBean(Class.forName(className));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load service class: " + className + "::" + e);
		}
		return clazz;
	}

	public List<String> getLineListeners() {
		return lineListeners;
	}

	public void setLineListeners(List<String> lineListeners) {
		this.lineListeners = lineListeners;
	}

	public List<String> getLetterSources() {
		return letterSources;
	}

	public void setLetterSources(List<String> letterSources) {
		this.letterSources = letterSources;
	}

	public void setDateProvider(String dateProvider) {
		this.dateProvider = dateProvider;
	}
}
