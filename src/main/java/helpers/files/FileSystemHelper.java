package helpers.files;

import java.io.File;

public class FileSystemHelper {
	public static String getFileFullPath(String contextDirectory, String targetFileName) {
		File directory = new File(contextDirectory);
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (file.getName().substring(0, file.getName().indexOf(".")).equals(targetFileName))
					return file.getAbsolutePath();
			}
		}
		return "";
	}
	
	public static String FileAbsolutePathToServerPath(String rootUrl, String targetFullPath) {
		if (rootUrl.isBlank() || rootUrl == null  || targetFullPath.isBlank() || targetFullPath == null)
			return "";
		
		String formatedTargetFullPath = targetFullPath.replace('\\', '/');
		formatedTargetFullPath = formatedTargetFullPath.substring(formatedTargetFullPath.indexOf(rootUrl));
		return formatedTargetFullPath;
	}
	
	public static String makeSaveFilePath(String targetDirectory, String fileName) {
		File fileUploadDir = new File(targetDirectory);
		if (!fileUploadDir.exists()) {
			fileUploadDir.mkdirs();
        }
		String fileFinalPath = new File(targetDirectory + fileName).getAbsolutePath();
			
		return fileFinalPath;
	}
}
