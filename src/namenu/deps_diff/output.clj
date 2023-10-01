(ns namenu.deps-diff.output
  (:require [clj-commons.ansi :as ansi]))

(defn make-ver [[type coord]]
  (case type
    :mvn (:mvn/version coord)
    :local (:local/root coord)
    :git (cond-> coord
                 :git/tag
                 :git/sha)))

(defn cli [{:keys [removed added modified]}]
  (println "Comparing between base and target:")
  (run! (fn [[name ver]]
          (ansi/pcompose
            [{:font  :red
              :width 10} "Removed"]
            "  "
            [{:font  :white
              :width 60
              :pad   :right}
             name
             "  "]
            [:yellow
             (make-ver ver)]))
        removed)

  (run! (fn [[name ver]]
          (ansi/pcompose
            [{:font  :green
              :width 10} "Added"]
            "  "
            [{:font  :white
              :width 60
              :pad   :right}
             name
             "  "]
            [:yellow
             (make-ver ver)]))
        added)

  (run! (fn [[name ver]]
          (ansi/pcompose
            [{:font  :blue
              :width 10} "Modified"]
            "  "
            [{:font  :white
              :width 60
              :pad   :right}
             name
             "  "]
            [:yellow
             (make-ver ver)]))
        modified))


(comment
  (do (ansi/pcompose
        [{:font  :white
          :width 80} "org.codehaus.plexus/plexus-sec-dispatcher"
         "  "]
        [{:font  :red
          :width 8
          :pad   :right} "Removed"]
        "  "
        [{:font :yellow}
         "1.0.0"])
      (ansi/pcompose
        [{:font  :white
          :width 80} "org.ow2.asm/asm"
         "  "]
        [{:font  :green
          :width 8
          :pad   :right} "Added"]
        "  "
        [{:font :yellow}
         "1.2.3.4"])
      (ansi/pcompose
        [{:font  :white
          :width 80} "org.eclipse.jetty/jetty-client"
         "  "]
        [{:font  :blue
          :width 8
          :pad   :right} "Modified"]
        "  "
        [{:font :yellow}
         "2.0"])))