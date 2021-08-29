package com.yc.qa.google.search;

/**
 *
 * @author limit (Yurii Chukhrai)
 */
public interface SearchPage {

	SearchPage searchContent(final String content);

	<T> T goToFirstLink(final Class<T> pageObjectModelPage);
}
