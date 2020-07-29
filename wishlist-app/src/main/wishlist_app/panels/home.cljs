(ns wishlist-app.panels.home)

(defn home []
  [:div.columns
   [:div.column.has-background-primary
    [:div.image.is-128x128
     [:img.is-rounded {:src "/img/img.jpeg"}]]]
   [:div.column.is-three-quarters.has-background-info]])
