package com.example.shopbee.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ChatBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.chat.adapter.ChatAdapter;
import com.example.shopbee.ui.common.base.BaseFragment;

public class ChatFragment extends BaseFragment<ChatBinding, ChatViewModel> implements ChatNavigator {
    ChatBinding binding;
    ChatAdapter chatAdapter = new ChatAdapter();
    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.chat;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        binding.chatRecyclerView.setAdapter(chatAdapter);
        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.chatRecyclerView.setOnClickListener(v->{
            hideKeyboard();
        });
        binding.chatMessageInput.setOnClickListener(v->{
            binding.chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
        });
        chatAdapter.addChat("\uD83D\uDC4B Hi there! Thanks for checking out Shopbee! \uD83C\uDF89 I'm your friendly Shopbee assistant, here to help you find amazing deals and discover awesome products. \uD83D\uDE0A Shopbee is a brand new online marketplace built with ❤\uFE0F by students at University of Science – Pham Minh Anh, Nguyen Minh Luan, and Nguyen Manh Dinh. They're passionate about connecting buyers and sellers in a fun, easy, and secure way. What can I help you with today? Here are some things I can do: * Tell you about Shopbee's features (like secure payment options, buyer/seller protections, and our awesome product categories!) * Help you find specific products (just tell me what you're looking for!) * Answer questions about your account (like how to track your orders or contact a seller). Just let me know what's on your mind! \uD83D\uDE04", false);
        binding.messageSendBtn.setOnClickListener(v->{
            if (!binding.chatMessageInput.getText().toString().isEmpty()){
                String message = binding.chatMessageInput.getText().toString();
                chatAdapter.addChat(message, true);
                viewModel.sendMessageWithStreamResponse(message);
                binding.chatMessageInput.setText("");
                binding.chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            }
        });
        binding.backBtn.setOnClickListener(v->{
            NavHostFragment.findNavController(this).navigateUp();
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.chatRecyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    @Override
    public void onResponse(String answer) {
        requireActivity().runOnUiThread(()->{
            chatAdapter.addChat(answer, false);
            binding.chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
        });
    }

    @Override
    public void onStreamingResponse(String chunk) {
        requireActivity().runOnUiThread(()->{
            chatAdapter.addCurrentChat(chunk);
            binding.chatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
        });
    }
}
