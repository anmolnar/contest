package org.dolphy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Renderer {
    private final ExecutorService executor;

    Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
        for (final ImageInfo imageInfo : info) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
        }
        renderText(source);
        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException ex) {
            throw launderThrowable(ex.getCause());
        }

    }

    private void renderImage(ImageData imageData) {
        System.out.println("Rendering image: " + imageData.name);
    }

    private List<ImageInfo> scanForImageInfo(CharSequence source) {
        return new ArrayList<>();
    }

    private void renderText(CharSequence source) {
        System.out.println("Rendering main text of the page");
    }

    static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error)t;
        else
            throw new IllegalStateException("Not unchecked", t);
    }
}

class ImageInfo {

    ImageData downloadImage() {
        return null;
    }
}

class ImageData {
    String name;

}