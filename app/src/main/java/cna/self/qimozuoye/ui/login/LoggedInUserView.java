package cna.self.qimozuoye.ui.login;

/**
 * 在显示账户信息之前又封装了一层
 * 虽然可以直接使用 Toast 和 LoggedInUser.getAccount();
 * 但这个文件可以在后期拓展处理功能
 */
class LoggedInUserView {
    private final String displayName;

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}