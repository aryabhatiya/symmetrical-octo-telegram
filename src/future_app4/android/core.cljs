(ns future-app4.android.core
  (:require-macros [rum.core :refer [defc]])
  (:require [re-natal.support :as support]
            [rum.core :as rum]))

(set! js/window.React (js/require "react"))
(def ReactNative (js/require "react-native"))

(defn create-element [rn-comp opts & children]
  (apply js/React.createElement rn-comp (clj->js opts) children))

(def app-registry (.-AppRegistry ReactNative))
(def view (partial create-element (.-View ReactNative)))
(def input (partial create-element (.-TextInput ReactNative)))
(def text (partial create-element (.-Text ReactNative)))
(def image (partial create-element (.-Image ReactNative)))
(def touchable-highlight (partial create-element (.-TouchableHighlight ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(defonce app-state (atom {:greeting "Hello Clojure in iOS and Android!"}))

(defc AppRoot < rum/reactive [state]
  (view {:style {:flexDirection "column" :margin 40 :alignItems "center" :backgroundColor "#ddd" }}
        (text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center" :color "blue"}}
              (:greeting (rum/react state)))
        (image {:source logo-img
                :style  {:width 80 :height 80 :marginBottom 30}})
        (touchable-highlight {:style   {:backgroundColor "#999" :padding 10 :borderRadius 5}
                              :onPress #(alert "HELLO!")}
                             (text {:style {:color "white" :textAlign "center" :fontWeight "bold"}} "press me"))
        (input {:style {:height 40
                        :width 100
                        }
                :onChangeText (fn [text]
                                (swap! app-state assoc :input text))})

        (text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center" }}
              (:input (rum/react state)))))

(defonce root-component-factory (support/make-root-component-factory))

(defn mount-app [] (support/mount (AppRoot app-state)))

(defn init []
      (mount-app)
      (.registerComponent app-registry "FutureApp4" (fn [] root-component-factory)))
