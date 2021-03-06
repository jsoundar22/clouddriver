/*
 * Copyright 2014 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.cats.provider;

import com.netflix.spinnaker.cats.agent.CacheResult;
import com.netflix.spinnaker.cats.cache.Cache;
import com.netflix.spinnaker.cats.cache.CacheData;

import java.util.Collection;

public interface ProviderCache extends Cache {
  /***
   * Used for storing the complete result set generated by a caching agent. Stored items not contained in
   * cacheResult are evicted.
   * @param source The calling caching agent.
   * @param authoritativeTypes Authoritative cache results define graph vertices, informative results create edges.
   * @param cacheResult The definitive set of results from source.
   */
  void putCacheResult(String source, Collection<String> authoritativeTypes, CacheResult cacheResult);

  /***
   * Equivalent to putCacheResult but no evictions are processed, even if explicitly within cacheResult
   * @param source The calling caching agent
   * @param authoritativeTypes Authoritative cache results define graph vertices, informative results create edges.
   *                           May only be of use with some ProviderCache implementations.
   * @param cacheResult Results to store. Since evictions are bypassed, partial authoritative results are supported.
   */
  void addCacheResult(String source, Collection<String> authoritativeTypes, CacheResult cacheResult);

  /***
   * Add or update a single authoritative resource.
   * @param type The calling caching agent.
   * @param cacheData Item to store.
   */
  void putCacheData(String type, CacheData cacheData);

  /***
   * Multi-get all identifiers of the given type.
   * @param type        All identifiers must be of this resource type.
   * @param identifiers The identifiers to fetch.
   * @return
   */
  Collection<CacheData> getAll(String type, Collection<String> identifiers);

  /***
   * Delete stored identifiers of the given type.
   * @param type All identifiers must be of this resource type.
   * @param ids The identifiers to delete.
   */
  void evictDeletedItems(String type, Collection<String> ids);
}
