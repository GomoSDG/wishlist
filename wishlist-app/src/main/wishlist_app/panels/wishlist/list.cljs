(ns wishlist-app.panels.wishlist.list
  (:require [re-frame.core :as re-frame]))

(def sites [{:id        :takealot.com
             :domains   ["www.takealot.co.za"
                         "takealot.co.za"]
             :selectors [{:root     ".pdp-main-panel"
                          :name     ".product-title h1"
                          :category ".product-title .subtitle"
                          :image    "image-box img"}]
             :colors    {:primary   "#007aff"
                         :secondary "#1c8644"}}])

(defn wishlist-item [item]
  [:div.box
   [:article.media
    [:figure.media-left
     [:p.image.is-64x64
      [:img {:src (:image-src item)}]]]
    [:div.media-content
     [:div.content
      [:p
       [:strong (:name item)]
       [:br]
       [:small (:category item)]]]
     [:nav.level.is-mobile
      [:div.level-left
       [:a.level-item {:href (:url item)
                       :target "_blank"}
        [:span.icon [:i.fa.fa-shopping-cart {:aria-hidden true}]]]]]]]])

(defn wishlist []
  (let [item {:name      "Asus TUF Gaming 15, AMD,8GB,HDD & SSD, GTX1650, 15.6\" Gaming N/Book-Black"
              :image-src "https://media.takealot.com/covers_tsins/69559930/4718017401845-1-pdpxl.jpg"
              :category  "AMD R7-3750H / 8GB DDR4 / 1TB HDD & 256GB SSD / GTX1650 4GB / 15.6\""
              :id        "PLID52619736"
              :url       "https://www.takealot.com/asus-tuf-gaming-15-amd-8gb-hdd-ssd-gtx1650-15-6-gaming-n-book-bl/PLID66382885"}]
    (fn []
      [:<>
       [wishlist-item item]
       [wishlist-item item]])))

(defn list-panel []
  [:div.columns
   [:div.column.is-3
    [:div.box
     [:p.title "Gomotso Lilokoe"]
     [:p.subtitle "Wishlist"]
     [:div.image
      [:img.is-rounded {:src "/img/img.jpeg"}]]]]
   [:div.column
    [wishlist]]])

(defn add-item-panel []
  [:div
   [:div.columns
    [:div.column
     [:div.box
      [:h1.title "Add Item"]
      [:h1.subtitle @(re-frame/subscribe [:wishlist-items/add-url])]]]]])

(re-frame/reg-event-db
 :set-add-url
 (fn [db [_ panel]]
   (js/console.log panel)
   (assoc db :wishlist-items/add-url panel)))

(re-frame/reg-sub
 :wishlist-items/add-url
 (fn [db]
   (:wishlist-items/add-url db)))
