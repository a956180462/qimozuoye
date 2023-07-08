package cna.self.qimozuoye.data;

/**
 * 很有趣的写法
 * Result的模板类
 * 包含两个子类：Success和Error
 */
public class Result<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private Result() {
    }

    //重新的toString,可以获取Result的信息
    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success success = (Result.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error) {
            Result.Error error = (Result.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success，成功就包含一份存储用户信息的数据，
    public final static class Success<T> extends Result {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error，失败就包含一个错误信息
    public final static class Error extends Result {
        private final Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}