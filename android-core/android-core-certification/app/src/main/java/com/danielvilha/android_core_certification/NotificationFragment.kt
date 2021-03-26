package com.danielvilha.android_core_certification

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.danielvilha.android_core_certification.databinding.FragmentNotificationBinding


/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : Fragment() {
    val REPLY_ACTION = "com.example.android.messagingservice.ACTION_MESSAGE_REPLY"
    val CONVERSATION_ID = "conversation_id"
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        // https://stackoverflow.com/questions/13902115/how-to-create-a-notification-with-notificationcompat-builder
        // https://developer.android.com/training/notify-user/build-notification
        binding?.buttonBasicNotification?.setOnClickListener {
            basicNotification()
        }

        binding?.buttonBasicNotificationWithIcon?.setOnClickListener {
            basicNotificationWithIcon()
        }

        binding?.buttonNotificationWithAction?.setOnClickListener {
            notificationWithAction()
        }

        binding?.buttonNotificationWithDirectReply?.setOnClickListener {
            notificationWithDirectReply()
        }

        binding?.buttonNotificationWithProgressBar?.setOnClickListener {
            notificationWithProgressBar()
        }
    }

    private fun basicNotification() {
        val intent = Intent(context!!.applicationContext, MainActivity::class.java)
        val CHANNEL_ID = "MYCHANNEL"
        val notificationChannel = NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW)
        val pendingIntent = PendingIntent.getActivity(context!!.applicationContext, 1, intent, 0)
        val notification: Notification = Notification.Builder(context!!.applicationContext, CHANNEL_ID)
                .setContentText("This is a basic notification")
                .setContentTitle("Basic Notification")
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .build()

        val notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(1, notification)
    }

    private fun basicNotificationWithIcon() {
        val intent = Intent(context!!.applicationContext, MainActivity::class.java)
        val CHANNEL_ID = "MYCHANNEL"
        val notificationChannel = NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW)
        val pendingIntent = PendingIntent.getActivity(context!!.applicationContext, 1, intent, 0)
        val notification: Notification = Notification.Builder(context!!.applicationContext, CHANNEL_ID)
                .setContentText("This is a basic notification with an icon")
                .setContentTitle("Basic Notification")
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chat)
                .build()

        val notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(1, notification)
    }

    private fun notificationWithAction() {
        val intent = Intent(context!!.applicationContext, MainActivity::class.java)

        val snoozeIntent = Intent(context, BroadcastReceiver::class.java).apply {
            action = getString(R.string.app_name)
            putExtra("EXTRA_NOTIFICATION_ID", 0)
        }
        val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0)

        val CHANNEL_ID = "MYCHANNEL"
        val notificationChannel = NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW)
        val pendingIntent = PendingIntent.getActivity(context!!.applicationContext, 1, intent, 0)
        val notification: Notification = Notification.Builder(context!!.applicationContext, CHANNEL_ID)
                .setContentText("This is a notification with the action")
                .setContentTitle("Notification with action")
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chat)
                .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
                        snoozePendingIntent)
                .build()

        val notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(1, notification)
    }

    private fun notificationWithDirectReply() {
        // Key for the string that's delivered in the action's intent.
        val CHANNEL_ID = "MYCHANNEL"
        val KEY_TEXT_REPLY = "key_text_reply"
        val notificationChannel = NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW)
        val replyLabel: String = resources.getString(R.string.reply_label)
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }

        // Build a PendingIntent for the reply action to trigger.
        val replyPendingIntent: PendingIntent =
                PendingIntent.getBroadcast(context,
                        12,//conversation.getConversationId(),
                        getMessageReplyIntent(13/*conversation.getConversationId()*/),
                        PendingIntent.FLAG_UPDATE_CURRENT)

        // Create the reply action and add the remote input.
        val action: Notification.Action =
                Notification.Action.Builder(R.drawable.ic_reply_icon,
                        getString(R.string.reply_label), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build()

        // Build the notification and add the action.
        val newMessageNotification = Notification.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chat)
                .setContentTitle("Notification with direct reply")
                .setContentText("This is a notification with direct reply")
                .addAction(action)
                .build()

        val notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        // Issue the notification.
        with(NotificationManagerCompat.from(context!!)) {
            notificationManager.notify(12, newMessageNotification)
        }
    }

    private fun notificationWithProgressBar() {
        val CHANNEL_ID = "MYCHANNEL"
        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID).apply {
            setContentTitle("Picture Download")
            setContentText("Download in progress")
            setSmallIcon(android.R.drawable.stat_sys_download)
            setPriority(NotificationCompat.PRIORITY_LOW)
        }
        val PROGRESS_MAX = 100
        val PROGRESS_CURRENT = 0
        NotificationManagerCompat.from(context!!).apply {
            // Issue the initial notification with zero progress
            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            notify(15/*notificationId*/, builder.build())

            // Do the job here that tracks the progress.
            // Usually, this should be in a
            // worker thread
            // To show progress, update PROGRESS_CURRENT and update the notification with:
            // builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
            // notificationManager.notify(notificationId, builder.build());

            // When done, update the notification one more time to remove the progress bar
            builder.setContentText("Download complete")
                    .setProgress(0, 0, false)
            notify(15/*notificationId*/, builder.build())
        }
    }

    // Creates an Intent that will be triggered when a voice reply is received.
    private fun getMessageReplyIntent(conversationId: Int): Intent? {
        return Intent()
                .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                .setAction(REPLY_ACTION)
                .putExtra(CONVERSATION_ID, conversationId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NotificationFragment.
         */
        @JvmStatic
        fun newInstance() =
            NotificationFragment().apply {}
    }
}