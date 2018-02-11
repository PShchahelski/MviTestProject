package com.android.mvi.test.domain.interactor.browse

import com.android.mvi.test.domain.executor.PostExecutionThread
import com.android.mvi.test.domain.executor.ThreadExecutor
import com.android.mvi.test.domain.interactor.FlowableUseCase
import com.android.mvi.test.domain.model.Contact
import com.android.mvi.test.domain.repository.ContactRepository
import io.reactivex.Flowable
import javax.inject.Inject

open class GetContactList @Inject constructor(val repository: ContactRepository,
                                              threadExecutor: ThreadExecutor,
                                              postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<Contact>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<Contact>> {
        return repository.getContacts()
    }

}