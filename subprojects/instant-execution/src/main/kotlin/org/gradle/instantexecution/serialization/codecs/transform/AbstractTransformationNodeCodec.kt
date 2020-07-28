/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.instantexecution.serialization.codecs.transform

import org.gradle.api.internal.artifacts.transform.TransformationNode
import org.gradle.instantexecution.serialization.Codec
import org.gradle.instantexecution.serialization.ReadContext
import org.gradle.instantexecution.serialization.WriteContext
import org.gradle.instantexecution.serialization.decodePreservingSharedIdentity
import org.gradle.instantexecution.serialization.encodePreservingSharedIdentityOf


internal
abstract class AbstractTransformationNodeCodec<T : TransformationNode> : Codec<T> {

    override fun WriteContext.encode(value: T) {
        encodePreservingSharedIdentityOf(value) { doEncode(value) }
    }

    override fun ReadContext.decode(): T =
        decodePreservingSharedIdentity { doDecode() }

    protected
    abstract fun WriteContext.doEncode(value: T)

    protected
    abstract fun ReadContext.doDecode(): T
}
