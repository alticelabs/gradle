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
package org.gradle.api.initialization.dsl;

import org.gradle.api.Action;
import org.gradle.api.Incubating;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.internal.HasInternalProtocol;

import java.io.File;
import java.util.List;

/**
 * A dependencies model builder. Dependencies defined via this model
 * will trigger the generation of accessors available in build scripts.
 *
 * @since 6.8
 */
@Incubating
@HasInternalProtocol
public interface DependenciesModelBuilder {

    /**
     * Configures the model by reading it from a local file.
     * Currently the only supported format is a .toml file, any attempt
     * to read another format would fail.
     *
     * @param modelFile the model file
     */
    void from(File modelFile);

    /**
     * Configures a dependency version which can then be referenced using
     * the {@link DependenciesModelBuilder.LibraryAliasBuilder#versionRef(String)} )} method.
     *
     * @param name an identifier for the version
     * @param versionSpec the dependency version spec
     * @return the version alias name
     */
    String version(String name, Action<? super MutableVersionConstraint> versionSpec);

    /**
     * Configures a dependency version which can then be referenced using
     * the {@link DependenciesModelBuilder.LibraryAliasBuilder#versionRef(String)} method.
     *
     * @param name an identifier for the version
     * @param version the version string
     */
    String version(String name, String version);

    /**
     * Entry point for registering an alias for a library
     * @param alias the alias identifer
     * @return a builder for this alias
     */
    AliasBuilder alias(String alias);

    /**
     * Declares a bundle of dependencies. A bundle consists of a name for the bundle,
     * and a list of aliases. The aliases must correspond to aliases defined via
     * the {@link #alias(String)} method.
     *
     * @param name the name of the bundle
     * @param aliases the aliases of the dependencies included in the bundle
     */
    void bundle(String name, List<String> aliases);

    /**
     * Returns the name of the extension configured by this builder
     */
    String getLibrariesExtensionName();

    /**
     * Allows configuring an alias
     *
     * @since 6.8
     */
    @Incubating
    interface AliasBuilder {
        /**
         * Sets GAV coordinates for this alias
         * @param groupArtifactVersion the GAV coordinates, in the group:artifact:version form
         */
        void to(String groupArtifactVersion);

        /**
         * Sets the group and name of this alias
         * @param group the group
         * @param name the name (or artifact id)
         * @return a builder to configure the version
         */
        LibraryAliasBuilder to(String group, String name);
    }

    /**
     * Allows configuring the version of a library
     *
     * @since 6.8
     */
    @Incubating
    interface LibraryAliasBuilder {
        /**
         * Configures the version for this alias
         */
        void version(Action<? super MutableVersionConstraint> versionSpec);

        /**
         * Configures the required version for this alias
         */
        void version(String version);

        /**
         * Configures this alias to use a version reference, created
         * via the {@link #version(String, Action)} method.
         *
         * @param versionRef the version reference
         */
        void versionRef(String versionRef);
    }
}
