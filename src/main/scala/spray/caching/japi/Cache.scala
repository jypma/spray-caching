package spray.caching.japi

import java.util.concurrent.CompletionStage
import java.util.function.Supplier
import scala.compat.java8.FutureConverters._
import scala.compat.java8.OptionConverters._
import scala.concurrent.ExecutionContext
import java.util.Optional
import scala.collection.JavaConverters._

class Cache[V](ec: ExecutionContext, delegate: spray.caching.Cache[V]) {
  private implicit val _ec = ec
  
  def get(key: Any, source: Supplier[CompletionStage[V]]): CompletionStage[V] = {
    delegate(key)(source.get.toScala).toJava
  }
  
  def getOptional(key: Any): Optional[CompletionStage[V]] = {
    delegate.get(key).map(_.toJava).asJava
  }
  
  def remove(key: Any): Optional[CompletionStage[V]] = {
    delegate.remove(key).map(_.toJava).asJava
  }
  
  def clear(): Unit = {
    delegate.clear()
  }
  
  def keys(): java.util.Set[Any] = {
    delegate.keys.asJava
  }
  
  def ascendingKeys(limit: Optional[Int]): java.util.Iterator[Any] = {
    delegate.ascendingKeys(limit.asScala).asJava
  }
  
  def ascendingKeys(): java.util.Iterator[Any] = {
    delegate.ascendingKeys().asJava
  }
  
  def size(): Int = {
    delegate.size
  }
}
