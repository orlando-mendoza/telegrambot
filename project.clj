(defproject telegrambot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [environ             "1.1.0"]
                 [morse               "0.4.3"]
                 [cheshire/cheshire   "5.9.0"]]

  :plugins [[lein-environ "1.1.0"]]

  :main ^:skip-aot telegrambot.core
  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}}

  :env {:telegram-token "933749967:AAEzyIPIdKgV0md5y6Tdp1eEGdFLYsaS-yk"
        :openweather-api-token "9512127432cafb5c1dd699b631c5d7bc"}

  )

