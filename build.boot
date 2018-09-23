(set-env!
  :resource-paths #{"src"}
  :dependencies '[[cider/piggieback "0.3.9" :scope "test" :exclusions [org.clojure/clojure]]
                  [adzerk/bootlaces "0.1.13"]
                  [weasel                  "0.7.0" :exclusions [org.clojure/clojure] :scope "test"]
                  [nrepl "0.4.5" :scope "test" :exclusions [org.clojure/clojure]]])

(def +version+ "0.4.0-SNAPSHOT")

(task-options!
  push {:repo "clojars"}
  pom  {:project     'vigilancetech/boot-cljs-repl
        :version     +version+
        :description "Boot task to provide a ClojureScript REPL."
        :url         "https://github.com/vigilancetech-com/boot-cljs-repl"
        :scm         {:url "https://github.com/vigilancetech-com/boot-cljs-repl"}
        :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})

(require '[adzerk.bootlaces :refer :all])

(deftask build []
  (comp
   (pom)
   (jar)
   (install)))

(deftask dev []
  (comp
    (watch)
    (build)
    (repl :server true)))

(deftask deploy []
  (comp
   (build)
   (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))
