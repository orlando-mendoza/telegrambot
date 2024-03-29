(ns telegrambot.core
  (:require [clojure.core.async :refer [<!!]]
            [clojure.string :as str]
            [environ.core :refer [env]]
            [morse.handlers :as h]
            [morse.polling :as p]
            [morse.api :as t]
            [cheshire.core :refer [parse-string]]
            [morse.api :as api])
  (:gen-class))

; TODO: fill correct token
(def token (env :telegram-token))

(def writer (clojure.java.io/writer "message.log" :append true))

; Accoding to openweather city followed by , and two letters for country works best eg. Tokyo, JP
(defn weather [city]
  (let [request (str "http://api.openweathermap.org/data/2.5/weather?q="
                     city
                     "&units=metric&APPID="
                     (env :openweather-api-token))]
    (:main
      (parse-string (slurp request) (fn [k] (keyword k))))))

(h/defhandler handler

              (h/command-fn "start"
                            (fn [{{id :id :as chat} :chat}]
                              (println "Bot joined new chat: " chat)
                              (t/send-text token id "Welcome to telegrambot!")))

              (h/command-fn "help"
                            (fn [{{id :id :as chat} :chat}]
                              (println "Help was requested in " chat)
                              (t/send-text token id "Help is on the way")))

              (h/command-fn "weather"
                            (fn [{{id :id} :chat :as message}]
                              (println "Weather was requested for"
                                       (str/replace (:text message) #"\/{1}(\w+)\s{1}" ""))
                              (let [place (str/replace (:text message) #"\/{1}(\w+)\s{1}" "")]
                                (try
                                  (t/send-text token id
                                               (str place "\n" (weather place)))
                                  (catch Exception e (println (.getMessage e)))))))

              (h/inline-fn
                (fn [inline]
                  (clojure.pprint/pprint inline writer)
                  (try
                    (t/answer-inline
                      token
                      (:id inline)
                      [{:type      "gif"
                        :id        "gif1"
                        :thumb_url "https://bit.ly/2DtXcIi"
                        :gif_url   "https://bit.ly/2DtXcIi"}])
                    (catch Exception e (clojure.pprint/pprint e)))
                  inline))

              (h/message-fn
                (fn [{{id :id} :chat :as message}]
                  (println "Intercepted message: " message)
                  (t/send-text token id "I don't do a whole lot ... yet.")
                  (clojure.pprint/pprint message writer)
                  (t/send-text token id (str/reverse (:text message))))))


(defn -main
                [& args]
                (when (str/blank? token)
                  (println "Please provide token in TELEGRAM_TOKEN environment variable!")
                  (System/exit 1))

                (println "Starting the telegrambot")
                (<!! (p/start token handler)))
