package com.rxjava.jun.rxjavaopdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.rxjava.jun.rxjavaopdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.tv_old)
    TextView tvOld;
    @Bind(R.id.bt_ThrottleFirst)
    Button btThrottleFirst;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.bt_ifgo)
    Button btIfgo;
    @Bind(R.id.bt_Buffer)
    Button btBuffer;
    @Bind(R.id.bt_Buffer2)
    Button btBuffer2;
    @Bind(R.id.bt_Zip)
    Button btZip;
    @Bind(R.id.bt_Merge)
    Button btMerge;
    @Bind(R.id.bt_Interval_on)
    Button btInterval_on;
    @Bind(R.id.bt_Interval_off)
    Button btInterval_off;
    @Bind(R.id.bt_Range)
    Button btRange;
    @Bind(R.id.bt_Repeat)
    Button btRepeat;
    @Bind(R.id.bt_Create)
    Button btCreate;
    @Bind(R.id.bt_From)
    Button btFrom;
    @Bind(R.id.bt_Defer)
    Button btDefer;
    @Bind(R.id.bt_Map)
    Button btMap;
    @Bind(R.id.bt_FlatMap)
    Button btFlatMap;
    @Bind(R.id.bt_PublishSubject)
    Button btPublishSubject;
    @Bind(R.id.bt_Scan)
    Button btScan;
    @Bind(R.id.bt_Distinct)
    Button btDistinct;
    @Bind(R.id.bt_Filter)
    Button btFilter;
    @Bind(R.id.bt_Skip_Take)
    Button btSkip_Take;




    private Context mContext;
    private Subscription subscription;
    private Integer i = 10;


    private Students s1 = new Students("赵日天");
    private Students s2 = new Students("陈铁柱");
    private Students s3 = new Students("王尼玛");
    private ArrayList<Students> list = new ArrayList<MainActivity.Students>();


    private List<Course> courseList = new ArrayList<MainActivity.Course>();
    private Course c1 = new Course("紅Buff");
    private Course c2 = new Course("蓝Buff");
    private Course c3 = new Course("火龙Buff");
    private Students s4;
    private Students s5;
    private Students s6;
    private ArrayList<Students> list2 = new ArrayList<MainActivity.Students>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_ThrottleFirst, R.id.bt_ifgo, R.id.bt_Buffer, R.id.bt_Buffer2,
            R.id.bt_Zip, R.id.bt_Merge, R.id.bt_Interval_on, R.id.bt_Interval_off,
            R.id.bt_Range, R.id.bt_Repeat, R.id.bt_Create, R.id.bt_From, R.id.bt_Defer,
            R.id.bt_Map, R.id.bt_FlatMap,R.id.bt_PublishSubject,R.id.bt_Scan,R.id.bt_Distinct,
            R.id.bt_Filter,R.id.bt_Skip_Take})
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * Skip操作符将源Observable发射的数据过滤掉前n项，
             * 而Take操作符则只取前n项，理解和使用起来都很容易，但是用处很大。
             *
             * 另外还有 SkipLast 和 TakeLast 操作符，分别是从后面进行过滤操作。
             */
            case R.id.bt_Skip_Take:
                tvOld.setText("输入：我不我是大帅锅");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                Observable.just("我","不","我","是","大","帅","锅")
                        .skip(2)
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                tvResult.append(s);
                            }
                        });
                break;
            /**
             * 只会返回满足过滤条件的数据
             *
             */
            case R.id.bt_Filter:
                tvOld.setText("输入：1,2,3,4,5");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                Observable.just(1,2,3,4,5)
                        .filter(new Func1<Integer, Boolean>() {
                            @Override
                            public Boolean call(Integer integer) {
                                return integer>=3;
                            }
                        }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tvResult.append("被筛选过的战斗力大于等于3的大神们是："+integer);
                    }
                });
                break;

            /**
             * Distinct操作符的用处就是用来去重，非常好理解，所有重复的数据都会被过滤掉。
             *
             * 而操作符DistinctUntilChanged则是用来过滤掉连续的重复数据。
             */
            case R.id.bt_Distinct:
                tvOld.setText("输入：1,2,3,1,1,2,1,4,5");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                Toast.makeText(mContext, "1,2,3,1,1,2,1,4,5 被剔除掉重复刷票的了~", Toast.LENGTH_SHORT).show();
                Observable.just(1,2,3,1,1,2,1,4,5)
                        .distinct()
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                            tvResult.append(integer+"");
                            }
                        });
                break;

            /**
             *   Scan操作符对一个序列的数据应用一个函数，
             *   并将这个函数的结果发射出去作为下个数据应用这个函数时候的第一个参数使用，
             *   有点类似于递归操作
             */
            case R.id.bt_Scan:
                tvOld.setText("输入：1,2,3,4,5");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                Observable.just(1,2,3,4,5)
                        .scan(new Func2<Integer, Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer, Integer integer2) {
                                return integer*integer2;
                            }
                        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tvResult.append(integer+"\n");
                    }
                });
                break;

            case R.id.bt_PublishSubject:
                startActivity(new Intent(mContext,PublishSubjectActivity.class));
                break;
            /**
             * 多对多变换
             * 如果要打印出每个学生所需要修的所有课程的名称呢？
             * （和Map的需求的区别在于，每个学生只有一个名字，但却有多个课程。）
             *
             */
            case R.id.bt_FlatMap:
                tvOld.setText("");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                courseList.clear();
                list2.clear();
                courseList.add(c1);
                courseList.add(c2);
                courseList.add(c3);
                s4 = new Students("赵日天", courseList);
                s5 = new Students("陈铁柱", courseList);
                s6 = new Students("王尼玛", courseList);
                list2.add(s4);
                list2.add(s5);
                list2.add(s6);
                Toast.makeText(mContext, "每个学生的每一项课程都被入侵扒出来了~~", Toast.LENGTH_SHORT).show();
                Observable.from(list2)
                        .flatMap(new Func1<Students, Observable<Course>>() {
                            @Override
                            public Observable<Course> call(Students students) {
                                tvResult.append("\n"+"学生" + students.getName() + "的课程如下：");
//                                Toast.makeText(mContext, "学生" + students.getName() + "的课程如下：", Toast.LENGTH_SHORT).show();
                                return Observable.from(students.getCourseList());
                            }
                        })
                        .subscribe(new Action1<Course>() {
                            @Override
                            public void call(Course course) {
                                tvResult.append(course.getcourseName()+"\n");
//                                Toast.makeText(mContext, course.getcourseName(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            /**
             * 一对一变换
             * 需求：假设有一个数据结构『学生』，现在需要打印出一组学生的名字。实现方式很简单
             *
             */
            case R.id.bt_Map:
                tvOld.setText("");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                list.clear();
                list.add(s1);
                list.add(s2);
                list.add(s3);
                Toast.makeText(mContext, "2017届全国大学生12级学渣获得者...", Toast.LENGTH_SHORT).show();
                Observable.from(list)
                        .map(new Func1<Students, Object>() {
                            @Override
                            public Object call(Students students) {
                                return students.getName();
                            }
                        }).subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        tvResult.append("Students name is :" + o.toString());
//                        Toast.makeText(mContext, "Students name is :" + o.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            /**
             *
             * defer操作符是直到有订阅者订阅时，才通过Observable的工厂方法创建Observable并执行，
             * defer操作符能够保证Observable的状态是最新的
             *
             * 这里：just操作符是在创建Observable就进行了赋值操作，
             * 而defer是在订阅者订阅时才创建Observable，此时才进行真正的赋值操作
             *
             * 所以，运行结果为：        just result:10          defer result:15
             */
            case R.id.bt_Defer:
                tvOld.setText("");
                tvResult.setText("");
                Observable justObservable = Observable.just(i);
                //注意：不能用int
                i = 12;
                Observable deferObservable = Observable
                        .defer(new Func0<Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call() {
                                return Observable.just(i);
                            }
                        });
                i = 15;
                justObservable.subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        Toast.makeText(mContext, "just result:" + o.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                deferObservable.subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        Toast.makeText(mContext, "defer result:" + o.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            /**
             * from操作符是把其他类型的对象和数据类型转化成Observable
             */
            case R.id.bt_From:
                tvOld.setText("输入：{0, 1, 2, 3, 4, 5}");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                Toast.makeText(mContext, "这里运用超能力，把一个整形数组转化为Observable", Toast.LENGTH_SHORT).show();
                Integer[] items = {0, 1, 2, 3, 4, 5};
                Observable.from(items)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                tvResult.append(integer.toString());
                            }
                        });

                break;
            /**
             * create操作符是所有创建型操作符的“根”，也就是说其他创建型操作符最后都是通过create操作符来创建Observable的
             */
            case R.id.bt_Create:
                tvOld.setText("");
                Toast.makeText(mContext, "就是说，Create是他们的爸爸⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄", Toast.LENGTH_SHORT).show();
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> observer) {
                        try {
                            if (!observer.isUnsubscribed()) {
                                for (int i = 1; i < 5; i++) {
                                    observer.onNext(i);
                                }
                                observer.onCompleted();
                            }
                        } catch (Exception e) {
                            observer.onError(e);
                        }
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tvResult.append(integer.toString());
                    }
                });
                break;
            /**
             * repeat操作符是对某一个Observable，重复产生多次结果，一般搭配Range 操作符使用
             */
            case R.id.bt_Repeat:
                tvOld.setText("");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                Toast.makeText(mContext, "我将带头冲锋，将会有两组重复的数据出现", Toast.LENGTH_SHORT).show();
                Observable.range(3, 3).repeat(2).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tvResult.append(integer.toString() + "  ");
                    }
                });
                break;
            /**
             * range操作符是创建一组在从n开始，个数为m的连续数字，比如range(3,10)，
             * 就是创建3、4、5…12的一组数字
             */
            case R.id.bt_Range:
                tvOld.setText("");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                Toast.makeText(mContext, "今晚欢乐透开奖号码已经在上方的TextView了^_^", Toast.LENGTH_SHORT).show();
                Observable.range(3, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                tvResult.append(integer.toString());
                            }
                        });
                break;

            /**
             * interval操作符是每隔一段时间就产生一个数字，这些数字从0开始，
             * 一次递增1直至无穷大
             */
            case R.id.bt_Interval_on:
                tvOld.setText("");
                Toast.makeText(mContext, "已经开始了，注意看上方的TextView的数字变化", Toast.LENGTH_SHORT).show();
                subscription = Observable.interval(2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                tvResult.setText(aLong.toString());
                                if (aLong >= 5) {
                                    btInterval_off.performClick();
                                    subscription.unsubscribe();
                                }
                            }
                        });
                break;
            case R.id.bt_Interval_off:
                tvOld.setText("");
                tvResult.setText("");
                if (subscription != null && subscription.isUnsubscribed())
                subscription.unsubscribe();
                Toast.makeText(mContext, "好吧，我被无情取消╭(╯^╰)╮", Toast.LENGTH_SHORT).show();
                break;


            /**
             * throttleFirst操作符：仅发送指定时间段内的第一个信号
             * 防止viw被重复点击
             */
            case R.id.bt_ThrottleFirst:
                tvOld.setText("");
                tvResult.setText("");
                RxView.clicks(btThrottleFirst)
                        .throttleFirst(3, TimeUnit.SECONDS)
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                Toast.makeText(mContext, "我是一个在3秒内不会被重复点击的按钮", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            /**
             * 场景:在用户登录界面时，如果用户未勾选同意用户协议，不允许登录
             */
            case R.id.bt_ifgo:
                tvOld.setText("");
                tvResult.setText("");
                RxCompoundButton.checkedChanges(checkBox)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean)
                                    Toast.makeText(mContext, "很机智嘛，就是在checkbox条件满足我才出现哦", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            /**
             * buffer(count):每接收到count个数据包裹，将这count个包裹打包，发送给订阅者
             * 案例：点击按钮达到指定次数后，触发某事件（即：View的N连击事件）
             * 不过，会出现，第一次的时候需要点4次才能触发，应该是这种用法的一个适合之处。
             */
            case R.id.bt_Buffer:
                tvOld.setText("");
                tvResult.setText("");
                RxView.clicks(btBuffer)
                        .buffer(3)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<Void>>() {
                            @Override
                            public void call(List<Void> voids) {
                                Toast.makeText(mContext, "也没什么啦~O(∩_∩)O", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            /**
             * Buffer(count , skip):每接收到count个数据后，将该count的个数据打包，并跳过第skip个数据，发送给订阅者
             * 案例：输入woqaiwni ， 两两发送，并跳过之后的一个数据，即最后订阅者接收到[1,2] [4,5] 两个数据包裹， 3和6 分别被跳过
             */

            case R.id.bt_Buffer2:
                tvOld.setText("输入：{\"w\", \"o\", \"q\", \"a\", \"i\", \"w\", \"n\", \"i\"}");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                String[] msg = {"w", "o", "q", "a", "i", "w", "n", "i"};
                Observable.from(msg)
                        .buffer(2, 3)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<String>>() {
                            @Override
                            public void call(List<String> strings) {
                                Toast.makeText(mContext, "" + strings, Toast.LENGTH_SHORT).show();
                                tvResult.append(strings+"\n");

                            }
                        });
                break;

            /**
             * 使用场景:
             * 当某界面内容来源不同，但需同时显示出来时
             * eg1:  一部分数据来自本地，一部分来自网络
             */
            case R.id.bt_Zip:
                tvOld.setText("");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                Observable.zip(
                        queryNewsForNet(),
                        queryNewsFromLocation(),
                        new Func2<List<News>, List<News>, Object>() {
                            @Override
                            public Object call(List<News> newses, List<News> newses2) {
                                newses.addAll(newses2);
                                return newses;
                            }
                        }
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                Toast.makeText(mContext, "合并后的数据:" + o.toString(), Toast.LENGTH_LONG).show();
                                tvResult.append(o.toString()+"\n");
                            }
                        });

                break;

            /**
             * 不要跟Zip操作符混淆，Merge其实只是将多个Obsevables的输出序列变为一个，
             * 方便订阅者统一处理，对于订阅者来说就仿佛只订阅了一个观察者一样。
             *
             * 先显示本地数据，然后三秒后请求道了网络数据，再显示为网络的数据
             */
            case R.id.bt_Merge:
                tvOld.setText("");
                tvResult.setText("");
                tvResult.append("输出:"+"\n");
                Observable.merge(
                        queryNewsFromLocation(),
                        queryNewsForNet()
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<News>>() {
                                       @Override
                                       public void call(List<News> newses) {
                                           Toast.makeText(mContext, "显示的数据为:" + newses.toString(), Toast.LENGTH_LONG).show();
                                            tvResult.append("显示的数据为:" + newses.toString()+"\n");
                                       }
                                   },
                                new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                });
                break;

        }

    }

    /**
     * 模拟网络新闻列表
     */
    private Observable<List<News>> queryNewsForNet() {
        return Observable.create(new Observable.OnSubscribe<List<News>>() {
            @Override
            public void call(Subscriber<? super List<News>> subscriber) {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<News> news = new ArrayList<>();
                news.add(new News("net:NEWS1"));
                news.add(new News("net:NEWS2"));
                news.add(new News("net:NEWS3"));
                subscriber.onNext(news);
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 模拟查询本地新闻信息
     */
    private Observable<List<News>> queryNewsFromLocation() {
        return Observable.create(new Observable.OnSubscribe<List<News>>() {
            @Override
            public void call(Subscriber<? super List<News>> subscriber) {

                ArrayList<News> news = new ArrayList<>();
                news.add(new News("location:News1"));
                news.add(new News("location:News2"));
                news.add(new News("location:News3"));
                subscriber.onNext(news);
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 模拟学生数据结构
     */
    public class News {

        private String name;

        public News(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "News{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    // 模拟学生结构
    class Students {
        private String name;
        private List<Course> courseList;

        public Students(String name) {
            this.name = name;
        }

        public Students(String name, List<Course> courseList) {
            this.name = name;
            this.courseList = courseList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Course> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<Course> courseList) {
            this.courseList = courseList;
        }
    }

    // 模拟课程数据结构
    class Course {
        private String courseName;

        public Course(String courseName) {
            this.courseName = courseName;
        }

        public String getcourseName() {
            return courseName;
        }

        public void setcourseName(String name) {
            this.courseName = name;
        }

    }
}