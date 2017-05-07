package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

/**
 * Created by jalexander on 5/2/17.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class TaggedImageRetriever {
    private static String baseUrl = "http://cs.jalexander.ninja:8080/";

    public static void getImageList(final ImageListResultListener listener) {
        RetrieveImageListTask retrieveImageListTask = new RetrieveImageListTask(listener);
        retrieveImageListTask.execute();
    }

    public static void getNumImages(final ImageNumResultListener listener) {
        RetrieveImageNumTask retrieveImageNumTask = new RetrieveImageNumTask(listener);
        retrieveImageNumTask.execute();
    }

    public static void getTaggedImage(String name, final TaggedImageResultListener listener) {
        RetrieveTaggedImageTask retrieveTaggedImageTask = new RetrieveTaggedImageTask(listener);
        retrieveTaggedImageTask.execute(name);
    }

    public static void getTaggedImageByIndex(int index, final TaggedImageResultListener listener) {
        RetrieveTaggedImageByIndexTask retrieveTaggedImageTask = new RetrieveTaggedImageByIndexTask(listener);
        retrieveTaggedImageTask.execute(new Integer(index));
    }

    interface ImageListResultListener {
        void onImageList(ArrayList<String> list);
    }

    interface ImageNumResultListener {
        void onImageNum(int num);
    }

    interface TaggedImageResultListener {
        void onTaggedImage(TaggedImage image);
    }

    static class TaggedImage {
        public Bitmap image;
        public ArrayList<String> tags;
        public Uri uri;

        public TaggedImage(Bitmap i, ArrayList<String> t) {
            image = i;
            tags = t;
        }
    }

    private static class RetrieveImageListTask extends AsyncTask<Void, Void, ArrayList<String>> {
        private ImageListResultListener listener;

        public RetrieveImageListTask(ImageListResultListener listener) {
            super();
            this.listener = listener;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try {
                URL pageUrl = new URL(baseUrl + "ls");
                URLConnection connection = pageUrl.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder pageHtmlBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    pageHtmlBuilder.append(inputLine);
                    pageHtmlBuilder.append('\n');
                }
                reader.close();
                String[] files = pageHtmlBuilder.toString().split("\n");
                ArrayList<String> picNames = new ArrayList<>();
                for (String file : files) {
                    picNames.add(file);
                }
                return picNames;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> picNames) {
            if (listener != null) {
                listener.onImageList(picNames);
            }
        }
    }

    private static class RetrieveImageNumTask extends AsyncTask<Void, Void, Integer> {
        private ImageNumResultListener listener;

        public RetrieveImageNumTask(ImageNumResultListener listener) {
            super();
            this.listener = listener;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                URL pageUrl = new URL(baseUrl + "num_dirs");
                URLConnection connection = pageUrl.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder pageHtmlBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    pageHtmlBuilder.append(inputLine);
                    pageHtmlBuilder.append('\n');
                }
                reader.close();

                return new Integer(Integer.parseInt(pageHtmlBuilder.toString().split("\n")[0]));
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Integer numImages) {
            if (listener != null) {
                if (numImages != null) {
                    listener.onImageNum(numImages.intValue());
                } else {
                    listener.onImageNum(-1);
                }
            }
        }
    }

    private static class RetrieveTaggedImageTask extends AsyncTask<String, Void, TaggedImage> {
        private TaggedImageResultListener listener;

        public RetrieveTaggedImageTask(TaggedImageResultListener listener) {
            super();
            this.listener = listener;
        }

        @Override
        protected TaggedImage doInBackground(String... params) {
            try {
                URL imageUrl = new URL(baseUrl + "file/" + params[0] + "/pic");
                URLConnection imageConnection = imageUrl.openConnection();
                Bitmap image = BitmapFactory.decodeStream(imageConnection.getInputStream());

                URL pageUrl = new URL(baseUrl + "file/" + params[0] + "/tags");
                URLConnection connection = pageUrl.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder pageHtmlBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    pageHtmlBuilder.append(inputLine);
                    pageHtmlBuilder.append('\n');
                }
                reader.close();
                String[] tags = pageHtmlBuilder.toString().split("\n");
                ArrayList<String> picTags = new ArrayList<String>(Arrays.asList(tags));
                return new TaggedImage(image, picTags);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(TaggedImage image) {
            if (listener != null) {
                listener.onTaggedImage(image);
            }
        }
    }

    private static class RetrieveTaggedImageByIndexTask extends AsyncTask<Integer, Void, TaggedImage> {
        private TaggedImageResultListener listener;

        public RetrieveTaggedImageByIndexTask(TaggedImageResultListener listener) {
            super();
            this.listener = listener;
        }

        @Override
        protected TaggedImage doInBackground(Integer... params) {
            try {
                URL imageUrl = new URL(baseUrl + "img/" + params[0] + "/pic");
                URLConnection imageConnection = imageUrl.openConnection();
                Bitmap image = BitmapFactory.decodeStream(imageConnection.getInputStream());

                URL pageUrl = new URL(baseUrl + "img/" + params[0] + "/tags");
                URLConnection connection = pageUrl.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder pageHtmlBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    pageHtmlBuilder.append(inputLine);
                    pageHtmlBuilder.append('\n');
                }
                reader.close();
                String[] tags = pageHtmlBuilder.toString().split("\n");
                ArrayList<String> picTags = new ArrayList<String>(Arrays.asList(tags));
                return new TaggedImage(image, picTags);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(TaggedImage image) {
            if (listener != null) {
                listener.onTaggedImage(image);
            }
        }
    }
}