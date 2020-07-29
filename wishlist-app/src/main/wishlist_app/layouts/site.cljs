(ns wishlist-app.layouts.site)

(defn navbar []
  [:nav.navbar.is-light {:aria-label "main navigation",
                         :role       "navigation"}
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
       [:a.button.is-light "Log in"]]]]]])

(defn site-layout [panel]
  [:<>
   [navbar]
   [:section.section
    [:div.container [panel]]]])
