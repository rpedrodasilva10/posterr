package com.posterr.utils;

import com.posterr.exception.BusinessException;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class OffsetBasedPageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = -123822277129613575L;

    private final int limit;
    private final int skip;
    private final Sort sort;

    @SneakyThrows
    public OffsetBasedPageRequest(int limit, int skip, Sort sort) {
        if (limit < 1) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Invalid value for parameter 'limit'", "Limit must be greater than or equal zero");
        }
        if (skip < 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Invalid value for parameter 'skip'", "Skip must be greater than or equal zero");
        }
        if (sort == null) {
            throw new IllegalArgumentException("Sort must not be null.");
        }
        this.limit = limit;
        this.skip = skip;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return skip / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return skip;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() + getPageSize()), getSort());
    }

    public Pageable previous() {
        return hasPrevious() ?
                new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() - getPageSize()), getSort()) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return skip > limit;
    }
}