(ns future-app4.android.core
  (:require-macros [rum.core :refer [defc]])
  (:require
   [re-natal.support :as support]
   [rum.core :as rum]))

(set! js/window.React (js/require "react"))
(def ReactNative (js/require "react-native"))

(defn create-element [rn-comp opts & children]
  (apply js/React.createElement rn-comp (clj->js opts) children))

(def app-registry (.-AppRegistry ReactNative))
(def view (partial create-element (.-View ReactNative)))
(def input (partial create-element (.-TextInput ReactNative)))
(def text  (partial create-element (.-Text ReactNative)))
(def image (partial create-element (.-Image ReactNative)))
(def touchable-highlight (partial create-element (.-TouchableHighlight ReactNative)))
(def list2  (partial create-element (.-List  ReactNative)))
(def flat-list  (partial create-element (.-FlatList  ReactNative)))


(def logo-img (js/require "./images/cljs.png"))


(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(defonce app-state (atom {:greeting "MD. ASHIK"}))

(defc AppRoot < rum/reactive [state]
  (view {:style {:flexDirection "column" :margin 40 :alignItems "center" :backgroundColor "#92819f" }}
        (text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center" :color "#C49BAB"}}
              (:greeting (rum/react state)))
        (image {:source logo-img
                :style  {:width 80 :height 80 :marginBottom 30}})
        (touchable-highlight {:style   {:backgroundColor "#E7A5A0" :padding 10 :borderRadius 5}
                              :onPress #(alert "HELLO!")}
                            (text {:style {:color "white" :textAlign "center" :fontWeight "bold"}} "press me"))
        (input {:style {:height 40
                        :width 100
                        :color "#FFBB85"
                        }
                :onChangeText (fn [text]
                                (swap! app-state assoc :input text))})

        (text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center" }}
              (:input (rum/react state)))
        (flat-list {:data (clj->js [{:key "1"
                                     :item "hello1"}
                                    {:key "2"
                                     :item "hello2"}
                                    {:key "3"
                                     :item "hello3"}
                                    {:key "4"
                                     :item "hello4"}
                                    {:key "5"
                                     :item "hello5"}
                                    {:key "7"
                                     :item "hello6"}
                                    {:key "8"
                                     :item "hello7"}
                                    {:key "9"
                                     :item "hello9"}
                                    {:key "10"
                                     :item "hello10"}
                                    {:key "11"
                                     :item "hello11"}
                                    {:key "12"
                                     :item "hello12"}
                                    {:key "13"
                                     :item "hello13"}
                                    {:key "15"
                                     :item "hello14"}
                                    {:key "16"
                                     :item "hello15"}
                                    {:key "18"
                                     :item "hello16"}
                                    {:key "19"
                                     :item "hello17"}
                                    ])
                    :renderItem (fn [d]
                                  (text {:style {:fontSize 30
                                                 :fontWeight "100"
                                                 :marginBottom 20
                                                 :textAlign "center" }}
                                        (.-item (aget d "item"))))})))

(defonce root-component-factory (support/make-root-component-factory))

(defn mount-app [] (support/mount (AppRoot app-state)))

(defn init []
      (mount-app)
      (.registerComponent app-registry "FutureApp4" (fn [] root-component-factory)))
