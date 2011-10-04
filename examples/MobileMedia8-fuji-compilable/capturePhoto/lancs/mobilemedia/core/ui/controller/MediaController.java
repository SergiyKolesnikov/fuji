package lancs.mobilemedia.core.ui.controller;
public class MediaController {
@MethodObject static class MediaController_handleCommand {
    protected void hook21(){
      if (label.equals("Capture Photo")) {
        playscree=new CaptureVideoScreen(_this.midlet,CaptureVideoScreen.CAPTUREPHOTO);
        playscree.setVisibleVideo();
        controller=new PhotoViewController(_this.midlet,_this.getAlbumData(),(AlbumListScreen)_this.getAlbumListScreen(),"New photo");
        controller.setCpVideoScreen(playscree);
        _this.setNextController(controller);
        playscree.setCommandListener(_this);
        throw new ReturnBoolean(true);
      }
      original();
    }
  }
}
