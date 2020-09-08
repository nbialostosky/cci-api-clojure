(ns cci-api.core
		(:gen-class)
		(:require [clojure.data.json :as json]))

;; Set workflow ID
(def workflow "https://circleci.com/api/v2/workflow/a1a7e440-8861-4560-856c-6a43c85117a9")
(def user "https://circleci.com/api/v2/user/")

;; Add personal API key here
(def api-key "")

(def workflow-url (str workflow "?circle-token=" api-key))

(def workflow-response (json/read-str
  (slurp workflow-url)))

(def user-id (get workflow-response "started_by"))

(def user-url (str user user-id "?circle-token=" api-key))

(def user-response (json/read-str
  (slurp user-url)))

;; name can be nil
(def name-value (get user-response "name"))

(def name (cond (nil? name-value) "No Name Provided" :else name-value))

(def login (get user-response "login"))

(def id (get user-response "id"))

(def who-dun-it? (str "The workflow was started by " name "/" login ", user-id for reference: " id))