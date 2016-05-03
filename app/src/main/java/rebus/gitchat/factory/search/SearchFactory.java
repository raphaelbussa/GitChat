/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Raphaël Bussa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rebus.gitchat.factory.search;

import org.cryse.widget.persistentsearch.SearchItem;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by raphaelbussa on 02/02/16.
 */
public class SearchFactory implements SearchSuggestionsBuilder {

    @Override
    public Collection<SearchItem> buildEmptySearchSuggestion(int maxCount) {
        return null;
    }

    @Override
    public Collection<SearchItem> buildSearchSuggestion(int maxCount, String query) {
        List<SearchItem> items = new ArrayList<>();
        if (query.startsWith("@")) {
            SearchItem toppicSuggestion = new SearchItem("Search User: " + query.substring(1), query, SearchItem.TYPE_SEARCH_ITEM_SUGGESTION);
            items.add(toppicSuggestion);
        } else if (query.startsWith("#")) {
            SearchItem toppicSuggestion = new SearchItem("Search Topic: " + query.substring(1), query, SearchItem.TYPE_SEARCH_ITEM_SUGGESTION);
            items.add(toppicSuggestion);
        } else {
            SearchItem peopleSuggestion = new SearchItem("Search People: " + query, "@" + query, SearchItem.TYPE_SEARCH_ITEM_SUGGESTION);
            items.add(peopleSuggestion);
            SearchItem toppicSuggestion = new SearchItem("Search Topic: " + query, "#" + query, SearchItem.TYPE_SEARCH_ITEM_SUGGESTION);
            items.add(toppicSuggestion);
        }
        return items;
    }
}
