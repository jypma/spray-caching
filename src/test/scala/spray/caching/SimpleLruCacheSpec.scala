/*
* Copyright © 2011-2015 the spray project <http://spray.io>
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package spray.caching

import org.specs2.mutable.Specification
import org.specs2.time.NoTimeConversions
import akka.actor.ActorSystem
import spray.util._
import org.specs2.concurrent.ExecutionEnv

class SimpleLruCacheSpec extends Specification with NoTimeConversions {
  implicit val system = ActorSystem()
  import system.dispatcher

  "not cache exceptions" in { implicit ee: ExecutionEnv => 
    val cache = new SimpleLruCache[String](10, 10)
    cache(1)((throw new RuntimeException("Naa")): String) must throwA[RuntimeException]("Naa").await
    cache(1)("A") must be("A").await
    cache.keys === Set(1)
    cache.ascendingKeys().toList === List(1)
  }

  "return ascendingKeys in order of least access first" in { implicit ee: ExecutionEnv =>
    val cache = new SimpleLruCache[String](10, 10)
    cache(1)("A") must be("A").await
    cache(2)("B") must be("B").await
    Thread sleep 1
    cache(1)("C") must be("A").await // 1 accessed more recently, should get cached value

    cache.keys === Set(1, 2)
    cache.ascendingKeys().toList === List(2, 1)
    cache.ascendingKeys(Some(1)).toList === List(2)
  }
}
