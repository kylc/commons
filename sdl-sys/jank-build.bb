(require '[clojure.string :refer [lower-case]]
         '[babashka.fs :as fs]
         '[jank.build.cmake :as cmake])

(let [src-dir (str (fs/path (:src-dir *input*) "lib" "SDL"))
      out-dir (:out-dir *input*)
      input   (assoc *input* :src-dir src-dir)]
  (cmake/build input {:defines {"CMAKE_INSTALL_LIBDIR" "lib"
                                ;; per https://wiki.libsdl.org/SDL3/README-macos
                                "CMAKE_OSX_DEPLOYMENT_TARGET" "10.13"
                                "SDL_WAYLAND" false
                                "SDL_TESTS" false
                                "SDL_EXAMPLES" false}})

  (println (str "jank-build::include-dir=" (fs/path out-dir "include")))
  (println (str "jank-build::link-dir=" (fs/path out-dir "lib")))
  (println (str "jank-build::link-library=" "SDL3"))

  (when (contains? #{"mac os x" "darwin"} (lower-case (System/getProperty "os.name")))
    (println "jank-build::link-framework=CoreMedia")
    (println "jank-build::link-framework=CoreVideo")
    (println "jank-build::link-framework=Cocoa")
    (println "jank-build::link-framework=IOKit")
    (println "jank-build::link-framework=ForceFeedback")
    (println "jank-build::link-framework=Carbon")
    (println "jank-build::link-framework=CoreAudio")
    (println "jank-build::link-framework=AudioToolbox")
    (println "jank-build::link-framework=AVFoundation")
    (println "jank-build::link-framework=CoreBluetooth")
    (println "jank-build::link-framework=CoreGraphics")
    (println "jank-build::link-framework=CoreMotion")
    (println "jank-build::link-framework=Foundation")
    (println "jank-build::link-framework=GameController")
    (println "jank-build::link-framework=Metal")
    (println "jank-build::link-framework=CoreHaptics")
    (println "jank-build::link-framework=QuartzCore")
    (println "jank-build::link-framework=UniformTypeIdentifiers")))
