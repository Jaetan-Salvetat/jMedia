#!/usr/bin/env bash

echo github.token="${secrets.GIT_TOKEN}" >> local.properties
echo 'theMovieDb.token="secrets.THE_MOVIE_DB_TOKEN }}" >> local.properties