(ns wishlist-app.panels.core)

(defn page-title [panel]
  (:page-title (meta panel)))

(defn page-layout [panel]
  (:navbar (meta panel)))
