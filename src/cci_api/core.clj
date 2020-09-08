(ns cci-api.core
		(:gen-class)
		(:require [clojure.data.json :as json]))

(def workflow "https://circleci.com/api/v2/workflow/")
(def user "https://circleci.com/api/v2/user/")

;; Add personal API key here
(def api-key "")

;; Set workflow ID
(def workflow-id "a1a7e440-8861-4560-856c-6a43c85117a9")

(defn url 
		[base-url base-id]
		(str base-url base-id))

(defn full-url
		[url]
		(str url "?circle-token=" api-key))

(defn value
		[response key]
		(get response key))

(def workflow-response (json/read-str
  (slurp (full-url (url workflow workflow-id)))))

(def user-id (get workflow-response "started_by"))

(def user-response (json/read-str
  (slurp (full-url (url user user-id)))))

;; name can be nil
(def user-name (cond (nil? (value user-response "name")) "No Name Provided" :else (value user-response "name")))

(def who-dun-it? (str "The workflow was started by " user-name "/" (value user-response "login") ", user-id for reference: " (value user-response "id")))