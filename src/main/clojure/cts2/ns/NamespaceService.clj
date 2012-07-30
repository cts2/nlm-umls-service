(ns cts2.ns
  (:require [clj-http.client :as client]))

;(gen-class 
;  :name edu.mayo.cts2.framework.plugin.service.nlm.namespace.NamespaceService
;  :methods [
;            #^{:static true} [getNsUri [String, String] String]
;            #^{:static true} [getNsPrefix [String, String] String]
;            ])

(defn- call
  [url response]
  (get
     (:body 
       (client/get url {:throw-exceptions false :accept :json :as :json})) (keyword response) ))

(def getNsUri 
 (memoize
   (fn [baseuri prefix]
    (call (str baseuri "/namespace/" prefix) "uri"))))

(def getNsPrefix 
  (memoize
    (fn [baseuri namespace]
      (call (str baseuri "/namespacebyuri?uri=" namespace) "content"))))
