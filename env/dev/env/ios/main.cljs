(ns ^:figwheel-no-load env.ios.main
  (:require [future-app4.ios.core :as core]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :heads-up-display false
  ;; TODO make this Rum something
  :jsload-callback #(#'core/mount-app))

(core/init)


;; Do not delete, root-el is used by the figwheel-bridge.js
(def root-el (core/root-component-factory))
