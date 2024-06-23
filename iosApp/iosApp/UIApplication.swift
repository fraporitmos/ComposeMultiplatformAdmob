//
//  UIApplication.swift
//  iosApp
//
//  Created by Fraporitmos on 22/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import UIKit

extension UIApplication {
    func getRootViewController() -> UIViewController? {
        // Obtener la escena conectada y la ventana principal
        guard let scene = connectedScenes.first as? UIWindowScene,
              let window = scene.windows.first else {
            return nil
        }
        // Devolver el rootViewController
        return window.rootViewController
    }
}
