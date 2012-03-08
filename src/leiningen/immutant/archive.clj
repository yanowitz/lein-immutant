(ns leiningen.immutant.archive
  (:require [clojure.java.io               :as io]
            [leiningen.core.classpath      :as classpath]
            [immutant.deploy-tools.archive :as archive]))

(defn ^{:internal true} copy-dependencies [project]
  (when project
    (let [dependencies (classpath/resolve-dependencies :dependencies project)
          lib-dir (io/file (:root project) "lib")]
      (println "Copying" (count dependencies) "dependencies to ./lib")
      (if-not (.exists lib-dir)
        (.mkdir lib-dir))
      (doseq [dep (map io/file dependencies)]
        (io/copy dep (io/file lib-dir (.getName dep)))))))

(defn archive
  "Creates an Immutant archive from a project"
  ([project root]
     (archive project root (System/getProperty "user.dir")))
  ([project root dest-dir]
     (binding [archive/*dependency-resolver* copy-dependencies]
       (let [jar-file (archive/create project root dest-dir)]
         (println "Created" (.getAbsolutePath jar-file))))))