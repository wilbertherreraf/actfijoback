package gob.gamo.activosf.app.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for handling pagination.
 *
 * <p>
 * Pagination uses the same principles as the
 * <a href="https://developer.github.com/v3/#pagination">GitHub API</a>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link
 * header)</a>.
 */
@Slf4j
public final class PaginationUtil {

    private PaginationUtil() {}

    public static <T> HttpHeaders generatePaginationHttpHeaders(Page<T> page, String baseUrl) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        String link = "";
        if ((page.getNumber() + 1) < page.getTotalPages()) {
            link = "<" + generateUri(baseUrl, page.getNumber() + 1, page.getSize()) + ">; rel=\"next\",";
        }
        // prev link
        if ((page.getNumber()) > 0) {
            link += "<" + generateUri(baseUrl, page.getNumber() - 1, page.getSize()) + ">; rel=\"prev\",";
        }
        // last and first link
        int lastPage = 0;
        if (page.getTotalPages() > 0) {
            lastPage = page.getTotalPages() - 1;
        }
        link += "<" + generateUri(baseUrl, lastPage, page.getSize()) + ">; rel=\"last\",";
        link += "<" + generateUri(baseUrl, 0, page.getSize()) + ">; rel=\"first\"";
        headers.add(HttpHeaders.LINK, link);
        return headers;
    }

    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("page", page)
                .queryParam("size", size)
                .toUriString();
    }

    public static <T> Page<T> pageForList(int pageIn, int size, List<T> list) {
        if (list == null || list.isEmpty()) {
            return new PageImpl<>(new ArrayList<>(), createPageRequestUsing(0, size), 0);
        }
        int page = pageIn <= 0 ? 1 : pageIn;
        size = size <= 0 ? 20 : size;
        int npages = (int) Math.ceil(list.size() / size);
        npages = list.size() > npages * size ? npages + 1 : npages;
        page = (page >= npages ? npages : page) - 1;

        Pageable pageRequest = createPageRequestUsing(page, size);
        int start = (int) pageRequest.getOffset();
        // start = start >= list.size() ? list.size() - 1 : start;
        int end = Math.min((start + pageRequest.getPageSize()), list.size());
        end = end >= list.size() ? list.size() : end;

        List<T> pageContent = list.subList(start, end);
        PageImpl<T> resp = new PageImpl<>(pageContent, pageRequest, list.size());
        return resp;
    }

    private static Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size <= 0 ? 1 : size);
    }
}
