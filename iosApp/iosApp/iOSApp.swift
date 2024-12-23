import SwiftUI

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
        ComposeView()
                .ignoresSafeArea(.keyboard)
                 .ignoresSafeArea(.container)// Compose has own keyboard handler
                  }
    }
}