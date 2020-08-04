(ns wishlist-app.layouts.site
  (:require [re-frame.core :as re-frame]
            [wishlist-app.panels.core :as panels]))



(defn nav []
  [:div.navbar-brand
   [:a.navbar-item {:href "#"}
    [:img {:src "/img/logo.png"}]]
   [:a.navbar-burger.burger
    {:data-target   "uiNav",
     :aria-expanded "false",
     :aria-label    "menu",
     :role          "button"}
    [:span {:aria-hidden "true"}]
    [:span {:aria-hidden "true"}]
    [:span {:aria-hidden "true"}]]]
  [:div#uiNav.navbar-menu
   [:div.navbar-start [:a.navbar-item "Home"]]
   [:div.navbar-end
    [:div.navbar-item
     [:div.buttons
      [:a.button.is-primary [:strong "Sign up"]]
      [:a.button.is-light "Log in"]]]]])

(defn navbar [{:keys [has-container? is-transparent?]}]
  [:nav.navbar {:aria-label "main navigation",
                :role       "navigation"
                :class      (if is-transparent?
                              "is-transparent"
                              "is-light")}
   (if has-container?
     [:div.container
      [nav]]
     [nav])])

(defn landing-layout [panel]
  [:section.hero.is-fullheight
   [:div.hero-head
    [navbar {:is-transparent? true
             :has-container?  true}]]

   [:div.hero-body
    [:div.container.has-text-centered
     [@panel]]]])

(defn default-layout [panel]
  (let [_ (println @panel)]
    [:<>
     [navbar]
     [:section.section
      [:div.container [@panel]]]]))

(def layouts {:landing #'landing-layout
              nil      #'default-layout})


(defn site-layout [panel]
  (let [panel  (re-frame/subscribe [:active-panel])
        layout (layouts (panels/page-layout @panel))]
    [layout panel]))
