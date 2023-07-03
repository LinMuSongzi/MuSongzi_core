import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.msz.filesystem.activity.DirActivity
import com.msz.filesystem.bean.IFile
import com.msz.filesystem.bean.IFile.Companion.isFile
import com.musongzi.core.util.ActivityThreadHelp

object JumActivity {


    fun starDirActivity(file: IFile, context: Context? = null) {
        if (!file.isFile()) {
            val c = context ?: ActivityThreadHelp.getCurrentApplication()
            val intent = Intent(c, DirActivity::class.java).apply {
                putExtra("dir", file.path)
                putExtra("info", file as Parcelable)
            }
            if (c is Application) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            c.startActivity(intent)
        }

    }

}