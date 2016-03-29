package spray.caching.japi

import java.time.Duration
import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext

object LruCache {
  /**
   * Creates a new [[spray.caching.ExpiringLruCache]] with timeToLive and timeToIdle 
   */
  def create[V](ec: ExecutionContext,
               maxCapacity: Int,
               initialCapacity: Int,
               timeToLive: Duration,
               timeToIdle: Duration): Cache[V] = {
    new Cache(ec, spray.caching.LruCache(maxCapacity, initialCapacity, 
      timeToLive = scala.concurrent.duration.Duration(timeToLive.toMillis(), TimeUnit.MILLISECONDS),
      timeToIdle = scala.concurrent.duration.Duration(timeToIdle.toMillis(), TimeUnit.MILLISECONDS)))
  }
  
  /**
   * Creates a new [[spray.caching.ExpiringLruCache]] with only timeToLive 
   */
  def createWithoutTTI[V](ec: ExecutionContext,
               maxCapacity: Int,
               initialCapacity: Int,
               timeToLive: Duration): Cache[V] = {
    new Cache(ec, spray.caching.LruCache(maxCapacity, initialCapacity, 
      timeToLive = scala.concurrent.duration.Duration(timeToLive.toMillis(), TimeUnit.MILLISECONDS)))
  }  
  
  /**
   * Creates a new [[spray.caching.ExpiringLruCache]] with only timeToIdle 
   */
  def createWithoutTTL[V](ec: ExecutionContext,
               maxCapacity: Int,
               initialCapacity: Int,
               timeToIdle: Duration): Cache[V] = {
    new Cache(ec, spray.caching.LruCache(maxCapacity, initialCapacity, 
      timeToIdle = scala.concurrent.duration.Duration(timeToIdle.toMillis(), TimeUnit.MILLISECONDS)))
  }
  
  /**
   * Creates a new [[spray.caching.SimpleLruCache]]  
   */
  def createSimple[V](ec: ExecutionContext,
               maxCapacity: Int,
               initialCapacity: Int): Cache[V] = {
    new Cache(ec, spray.caching.LruCache(maxCapacity, initialCapacity))
  }
  
}
