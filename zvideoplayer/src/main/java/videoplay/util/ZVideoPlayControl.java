package videoplay.util;


import videoplay.ZVideoView;

/**
 * 供外界界面用来配置播放器的属性,然后播放的工具,对用户可见,用户的操作入口只有它
 *
 * @author 张科
 * @date 2019/3/18.
 */
public class ZVideoPlayControl {

    public static void start(ZVideoView videoView, ZVideoParamBuilder builder) {
        videoView.setBuilder(builder).start();
    }

    public static void setBuild(ZVideoView videoView, ZVideoParamBuilder builder) {
        videoView.setBuilder(builder);
    }

    public static void start(ZVideoView videoView) {
        videoView.start();
    }


    public static class ZVideoParamBuilder {

        private String videoURL;

        /**
         * 默认使用方式二TextureView
         */
        private boolean userTextureView = true;

        private boolean isLooping = false;

        private ZVideoParamBuilder() {

        }

        public static ZVideoParamBuilder obtain() {
            return new ZVideoParamBuilder();
        }

        public String getVideoURL() {
            return videoURL;
        }

        public ZVideoParamBuilder setVideoURL(String videoURL) {
            this.videoURL = videoURL;
            return this;
        }

        public boolean isUserTextureView() {
            return userTextureView;
        }

        public ZVideoParamBuilder setUserTextureView(boolean userTextureView) {
            this.userTextureView = userTextureView;
            return this;
        }

        public boolean isLooping() {
            return isLooping;
        }

        public ZVideoParamBuilder setLooping(boolean looping) {
            isLooping = looping;
            return this;
        }
    }
}
