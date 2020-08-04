(ns wishlist-app.components.modal)

(def default-modal-config)

(defn modal
  ([{:keys [is-active title]} body]
   (let []
     [:div.modal {:class (when @is-active "is-active")}
      [:div.modal-background]
      [:div.modal-card
       [:header.modal-card-head
        [:p.modal-card-title title]
        [:button.delete {:aria-label "close"
                         :on-click #(reset! is-active false)}]]
       [:section.modal-card-body
        (if (fn? body)
          [body]
          body)]
       [:footer.modal-card-foot]]]))
  ([body]
   (modal {:is-active (atom false)} body)))
