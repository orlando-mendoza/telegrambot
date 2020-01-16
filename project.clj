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

  :env {:telegram-token "your-telegram-token-here"
        :openweather-api-token "your-openweather-api-token-here"}

  )

