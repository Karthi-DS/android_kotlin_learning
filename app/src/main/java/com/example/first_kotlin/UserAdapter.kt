package com.example.first_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.first_kotlin.data.User

class UserAdapter(
    private val onEdit: (User) -> Unit,
    private val onDelete: (User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tvUserName)
        private val tvEmail = itemView.findViewById<TextView>(R.id.tvUserEmail)
        private val btnEdit = itemView.findViewById<ImageButton>(R.id.btnEdit)
        private val btnDelete = itemView.findViewById<ImageButton>(R.id.btnDelete)

        fun bind(user: User) {
            tvName.text = user.name
            tvEmail.text = user.email
            btnEdit.setOnClickListener { onEdit(user) }
            btnDelete.setOnClickListener { onDelete(user) }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
    }
}
