package com.inventory.rayli.common.bo;

import io.swagger.annotations.ApiModelProperty;

import java.io.File;
import java.io.Serializable;

public class FileSaveInfo implements Serializable {
    @ApiModelProperty("文件相对路径：2021/202106/20210608/1.jpg")
    private String userPath;
    @ApiModelProperty("文件相对路径：upload/2021/202106/20210608/1.jpg")
    private String relativeUrl;
    @ApiModelProperty("上级目录：D://projectName/bing/files")
    private File parentFolder;

    public FileSaveInfo() {
    }

    public String getUserPath() {
        return this.userPath;
    }

    public String getRelativeUrl() {
        return this.relativeUrl;
    }

    public File getParentFolder() {
        return this.parentFolder;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public void setParentFolder(File parentFolder) {
        this.parentFolder = parentFolder;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FileSaveInfo)) {
            return false;
        } else {
            FileSaveInfo other = (FileSaveInfo)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$userPath = this.getUserPath();
                    Object other$userPath = other.getUserPath();
                    if (this$userPath == null) {
                        if (other$userPath == null) {
                            break label47;
                        }
                    } else if (this$userPath.equals(other$userPath)) {
                        break label47;
                    }

                    return false;
                }

                Object this$relativeUrl = this.getRelativeUrl();
                Object other$relativeUrl = other.getRelativeUrl();
                if (this$relativeUrl == null) {
                    if (other$relativeUrl != null) {
                        return false;
                    }
                } else if (!this$relativeUrl.equals(other$relativeUrl)) {
                    return false;
                }

                Object this$parentFolder = this.getParentFolder();
                Object other$parentFolder = other.getParentFolder();
                if (this$parentFolder == null) {
                    if (other$parentFolder != null) {
                        return false;
                    }
                } else if (!this$parentFolder.equals(other$parentFolder)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof FileSaveInfo;
    }


    public String toString() {
        return "FileSaveInfo(userPath=" + this.getUserPath() + ", relativeUrl=" + this.getRelativeUrl() + ", parentFolder=" + this.getParentFolder() + ")";
    }
}